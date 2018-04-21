package org.drombler.jstore.client.service;

import org.drombler.commons.client.startup.main.ApplicationInstanceListener;
import org.drombler.commons.client.startup.main.CommandLineArgs;
import org.drombler.commons.client.startup.main.DromblerClientConfiguration;
import org.drombler.commons.client.startup.main.DromblerClientStarter;



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
