package org.drombler.jstore.client.service.jre.oracle.windows.browserdriver;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import jdk.incubator.http.HttpClient;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

//import org.drombler.jstore.client.service.commons.HttpClientUtils;
//import org.drombler.jstore.client.service.jre.JREInstaller;


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
public class WindowsJRE8Installer2 {//implements JREInstaller {

    private final JBrowserDriver driver;
    private HttpClient httpClient;

    public WindowsJRE8Installer2(JBrowserDriver driver, HttpClient httpClient) {
        this.driver = driver;
        this.httpClient = httpClient;
    }

    // @Override
    public void installJRE(Path installationDirPath) throws InterruptedException, ExecutionException, TimeoutException, IOException {

        // This will block for the page load and any
        // associated AJAX requests
        driver.get("http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html");

        // You can get status code unlike other Selenium drivers.
        // It blocks for AJAX requests and page loads after clicks
        // and keyboard events.
        System.out.println(driver.getStatusCode());

        // Returns the page source in its current state, including
        // any DOM updates that occurred after page load
//        System.out.println(driver.getPageSource());
        WebDriverWait wait = new WebDriverWait(driver, 100);
        acceptAgreement(wait);
        System.out.println("Cookies before: " + driver.manage().getCookies());
        driver.executeScript("disableDownloadAnchors(document, false, 'jre-8u161-oth-JPR'); hideAgreementDiv(document, 'jre-8u161-oth-JPR'); writeSessionCookie( 'oraclelicense', 'accept-securebackup-cookie' );");
        System.out.println("Cookies after: " + driver.manage().getCookies());
        //        WebElement radioInput2= wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("Accept License Agreement")));
//radioInput.click();
//        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("jre-8u161-windows-x64.tar.gz")));
        WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("jre-8u161-windows-x64.tar.gz")));
//        wait.until(ExpectedConditions.attributeToBe(link, "onClick", null));
        wait.until(ExpectedConditions.attributeContains(link, "href", "jre"));
        System.out.println(link.getText());
        System.out.println("href=" + link.getAttribute("href"));
        System.out.println("onClick=" + link.getAttribute("onClick"));

//        link.click();
//        driver.get(link.getAttribute("href"));

//        HttpClientUtils.importCookies(httpClient, driver);
//        HttpClientUtils.downloadFile(httpClient, URI.create(link.getAttribute("href")),
//                installationDirPath.resolve(link.getText()));
    }

    private void acceptAgreement(WebDriverWait wait) {
        WebElement agreementDivElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("agreementDivjre-8u161-oth-JPR")));
//        WebElement radioInput = agreementDivElement.findElement(By.xpath("./form/input[1]"));
        WebElement radioInput = agreementDivElement.findElements(By.tagName("input")).get(0);
        System.out.println(radioInput.getText());
        System.out.println(radioInput.getAttribute("onClick"));
        System.out.println("checked=" + radioInput.getAttribute("checked"));
        System.out.println("Selected: " + radioInput.isSelected());
        radioInput.click();
//        wait.until(ExpectedConditions.invisibilityOf(radioInput));
        WebElement thankYouDivElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("thankYouDivjre-8u161-oth-JPR")));

        System.out.println("Selected: " + radioInput.isSelected());
        System.out.println("checked=" + radioInput.getAttribute("checked"));

    }

    //    @Override
    public void uninstallJRE(Path installationDirPath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
