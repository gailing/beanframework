package ro.szzsa.flashair.connector;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

/**
 *
 */
public class HttpConnector {

    private static final String TEMP_FOLDER_NAME = "temp";

    private static final int DEFAULT_CONNECTION_TIMEOUT = 10000;

    private static final int DEFAULT_READ_TIMEOUT = 60000;

    private static final int DEFAULT_BUFFER_SIZE = 67108864;

    private static final int DEFAULT_CONNECTION_RETRY_DELAY = 3000;

    private static final int DEFAULT_MAX_CONNECTION_RETRY_COUNT = 2;

    private Logger log;

    private int connectionTimeout;

    private int readTimeout;

    private int bufferSize;

    private int connectionRetryDelay;

    private int maxConnectionRetryCount;

    public HttpConnector() {
        log = org.apache.log4j.Logger.getLogger(getClass().getName());
        connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
        readTimeout = DEFAULT_READ_TIMEOUT;
        bufferSize = DEFAULT_BUFFER_SIZE;
        connectionRetryDelay = DEFAULT_CONNECTION_RETRY_DELAY;
        maxConnectionRetryCount = DEFAULT_MAX_CONNECTION_RETRY_COUNT;
    }

    public String doRequest(final String url) throws Exception {
        return handleConnection(new Connection() {
            @Override
            public String connect() throws Exception {
                return sendRequest(url);
            }
        });
    }

    public void downloadFile(final String url, final String fileName, final String dir) throws ConnectorException {
        handleConnection(new Connection() {
            @Override
            public String connect() throws ConnectorException {
                download(url, fileName, dir);
                return null;
            }
        });
    }

    public void download(String url, String fileName, String dir) throws ConnectorException {
        log.info("Downloading file from " + url);
        if (dirExists(TEMP_FOLDER_NAME)) {
            String temp = TEMP_FOLDER_NAME + "/" + fileName;
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setConnectTimeout(connectionTimeout);
                connection.setReadTimeout(readTimeout);
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    try (InputStream input = connection.getInputStream();
                         OutputStream output = new FileOutputStream(temp)) {
                        byte buffer[] = new byte[bufferSize];
                        int count;
                        while ((count = input.read(buffer)) != -1) {
                            output.write(buffer, 0, count);
                        }
                        Files.copy(Paths.get(temp), Paths.get(dir + "/" + fileName), StandardCopyOption.REPLACE_EXISTING);
                        log.info(fileName + " downloaded");
                    } catch (Exception e) {
                        throw new ConnectorException(e);
                    }
                } else {
                    log.error("Cannot download file: status " + connection.getResponseCode());
                }
            } catch (Exception e) {
                throw new ConnectorException(e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    Files.deleteIfExists(Paths.get(temp));
                } catch (IOException e) {
                    log.error("Cannot delete temporary file " + fileName, e);
                }
            }
        }
    }

    public boolean dirExists(String path) {
        boolean dirExists = false;
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            try {
                log.info("Creating directory " + path);
                dirExists = dir.mkdir();
                dirExists = true;
            } catch (SecurityException e) {
                log.error("Cannot create directory " + path, e);
            }
        } else {
            dirExists = true;
        }
        return dirExists;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void setConnectionRetryDelay(int connectionRetryDelay) {
        this.connectionRetryDelay = connectionRetryDelay;
    }

    public void setMaxConnectionRetryCount(int maxConnectionRetryCount) {
        this.maxConnectionRetryCount = maxConnectionRetryCount;
    }

    private String sendRequest(String url) throws ConnectorException {
        log.info("Sending request to " + url);
        final RequestConfig requestConfig = RequestConfig.custom()
                                                .setConnectTimeout(connectionTimeout)
                                                .setSocketTimeout(readTimeout)
                                                .build();
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()) {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            try (InputStream inputStream = entity.getContent()) {
                return new String(IOUtils.toByteArray(inputStream));
            }
        } catch (Exception e) {
            throw new ConnectorException(e);
        }
    }

    private String handleConnection(Connection connection) throws ConnectorException {
        while (connection.canRetry()) {
            try {
                return connection.connect();
            } catch (ConnectException | SocketTimeoutException e) {
                log.error(e.getMessage());
                connection.retry();
                try {
                    Thread.sleep(connectionRetryDelay);
                } catch (InterruptedException e1) {
                    throw new ConnectorException(e);
                }
                log.debug("Retrying...");
            } catch (Exception e) {
                throw new ConnectorException(e);
            }
        }
        log.error("Maximum retry count reached");
        throw new ConnectorException("Maximum retry count reached");
    }

    private abstract class Connection {

        private int retriesLeft = maxConnectionRetryCount;

        public abstract String connect() throws Exception;

        public void retry() {
            retriesLeft--;
        }

        public boolean canRetry() {
            return retriesLeft >= 0;
        }
    }
}
