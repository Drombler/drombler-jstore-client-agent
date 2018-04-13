package org.drombler.jstore.client.service.jre.windows;

/**
 *
 * @author puce
 */
public class WindowsJRE8Installer implements JREInstaller {

    private final HttpClient httpClient;

    public WindowsJRE8Installer(HttpClient httpClient){
        this.httpClient = httpClient;
    }
    
    @Override
    public void installJRE(Path installationDirPath) {
        try {


            HttpRequest.newBuilder()
                    .uri(new URI("http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html"))
                    .timeout(Duration.of(10, SECONDS))
                    .GET()
                    .build();
        } catch (URISyntaxException ex) {
            Logger.getLogger(WindowsJRE8Installer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void uninstallJRE(Path installationDirPath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
