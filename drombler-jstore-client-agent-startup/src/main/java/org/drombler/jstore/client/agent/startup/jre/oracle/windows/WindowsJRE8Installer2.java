package org.drombler.jstore.client.agent.startup.jre.oracle.windows;

import jdk.incubator.http.HttpClient;
import org.drombler.jstore.client.agent.startup.commons.HttpClientUtils;
import org.drombler.jstore.client.agent.startup.jre.JREInstaller;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


/**
 * see https://github.com/docker-library/official-images/issues/625
 * <p>
 * <p>
 * Ubuntu stopped distributing it in the sun-java6 package when Oracle retired the "Operating System Distributor License for Java" (lists.ubuntu.com).
 * The webupd8 ppa for Ubuntu and Debian requires the user to accept the Oracle license in order for their software to download and install Oracle java (webupd8,org).
 * Gentoo has a fetch-restriction that requires the user to go to the Oracle website to download the Java tar manually which inclues accepting the license (wiki.gentoo.org).
 * CentOS requires users to go and download the rpm provided by Oracle at java.com and thus accept the Oracle license (wiki.centos.org).
 * RedHat provides instructions to add a repo maintained by Oracle (access.redhat.com).
 * <p>
 * As all of the major upstream Linux distributions are unable to redistribute Oracle java in their own distribution channels, I think it would be wise if we did the same.
 * <p>
 * <p>
 * <p>
 * https://gist.github.com/P7h/9741922
 *
 * @author puce
 */
public class WindowsJRE8Installer2 implements JREInstaller {

    private HttpClient httpClient;

    public WindowsJRE8Installer2(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    // @Override
    public void installJRE(Path installationDirPath) throws InterruptedException, ExecutionException, TimeoutException, IOException {


//        link.click();
//        driver.get(link.getAttribute("href"));

//        HttpClientUtils.importCookies(httpClient, driver);
        String link = "http://download.oracle.com/otn-pub/java/jdk/8u172-b11/a58eab1ec242421181065cdc37240b08/jre-8u172-windows-x64.tar.gz";
        String fileName = "jre-8u172-windows-x64.tar.gz";
        HttpClientUtils.downloadFile(httpClient, URI.create(link),
                installationDirPath.resolve(fileName));
    }


    //    @Override
    public void uninstallJRE(Path installationDirPath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
