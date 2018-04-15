package org.drombler.jstore.client.service.jre.windows;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;

import org.drombler.jstore.client.service.jre.JREInstaller;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 *
 * @author puce
 */
public class WindowsJRE8Installer2 implements JREInstaller {

    private final JBrowserDriver driver;

    public WindowsJRE8Installer2(JBrowserDriver driver){
        this.driver = driver;
    }
    
    @Override
    public void installJRE(Path installationDirPath) {

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
        driver.executeScript("disableDownloadAnchors(document, false, 'jre-8u161-oth-JPR'); hideAgreementDiv(document, 'jre-8u161-oth-JPR'); writeSessionCookie( 'oraclelicense', 'accept-securebackup-cookie' );");
//        WebElement radioInput2= wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("Accept License Agreement")));
//radioInput.click();
//        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("jre-8u161-windows-x64.tar.gz")));
        WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("jre-8u161-windows-x64.tar.gz")));
//        wait.until(ExpectedConditions.attributeToBe(link, "onClick", null));
        wait.until(ExpectedConditions.attributeContains(link, "href", "jre"));

        System.out.println("href="+link.getAttribute("href"));
        System.out.println("onClick="+link.getAttribute("onClick"));
        link.click();

//        driver.get(link.getAttribute("href"));

    }

    private void acceptAgreement(WebDriverWait wait) {
        WebElement agreementDivElement= wait.until(ExpectedConditions.presenceOfElementLocated(By.id("agreementDivjre-8u161-oth-JPR")));
//        WebElement radioInput = agreementDivElement.findElement(By.xpath("./form/input[1]"));
        WebElement radioInput = agreementDivElement.findElements(By.tagName("input")).get(0);
        System.out.println(radioInput.getText());
        System.out.println(radioInput.getAttribute("onClick"));
        System.out.println("Selected: "+radioInput.isSelected());
        radioInput.click();
//        wait.until(ExpectedConditions.invisibilityOf(radioInput));
        WebElement thankYouDivElement= wait.until(ExpectedConditions.presenceOfElementLocated(By.id("thankYouDivjre-8u161-oth-JPR")));

        System.out.println("Selected: "+radioInput.isSelected());
    }

    @Override
    public void uninstallJRE(Path installationDirPath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
