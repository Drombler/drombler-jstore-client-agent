package org.drombler.jstore.client.service;

import jdk.incubator.http.HttpClient;
import org.drombler.commons.client.startup.main.*;

import java.net.CookieManager;
import java.net.ProxySelector;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.machinepublishers.jbrowserdriver.Timezone;
import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import org.drombler.jstore.client.service.jre.oracle.windows.WindowsJRE8Installer2;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;

/**
 *
 * @author puce
 */
public class JStoreClientServiceApplication extends DromblerClientStarter<DromblerClientConfiguration> {

    public static void main(String[] args) throws Exception {
        CommandLineArgs commandLineArgs = CommandLineArgs.parseCommandLineArgs(args);
        DromblerClientConfiguration configuration = new DromblerClientConfiguration(commandLineArgs);
        JStoreClientServiceApplication application = new JStoreClientServiceApplication(configuration);

        if (application.init()) {
            application.start();
        }

    }

    public JStoreClientServiceApplication(DromblerClientConfiguration configuration){
        super(configuration);
        addAdditionalStarters(new UpdateSchedulerStarter());
    }

    @Override
    protected ApplicationInstanceListener getApplicationInstanceListener() {
        return additionalArgs -> {
            if (additionalArgs.size() == 1 && additionalArgs.get(0).equals("stop")){
                stop();
            }
        };
    }
}
