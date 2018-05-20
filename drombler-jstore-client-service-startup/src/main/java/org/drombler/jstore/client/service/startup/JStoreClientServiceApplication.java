package org.drombler.jstore.client.service.startup;

import org.drombler.commons.client.startup.main.ApplicationInstanceListener;
import org.drombler.commons.client.startup.main.DromblerClientStarter;
import org.drombler.commons.client.startup.main.cli.CommandLineArgs;


/**
 * @author puce
 */
public class JStoreClientServiceApplication extends DromblerClientStarter<JStoreClientServiceConfiguration> {

    public static void main(String[] args) throws Exception {
        CommandLineArgs commandLineArgs = CommandLineArgs.parseCommandLineArgs(args);
        JStoreClientServiceConfiguration configuration = new JStoreClientServiceConfiguration(commandLineArgs);
        JStoreClientServiceApplication application = new JStoreClientServiceApplication(configuration);

        if (application.init()) {
            application.start();
        }
        Thread.sleep(100000);
    }

    public JStoreClientServiceApplication(JStoreClientServiceConfiguration configuration) {
        super(configuration);
        addAdditionalStarters(new UpdateSchedulerStarter(configuration));
    }

    @Override
    protected ApplicationInstanceListener getApplicationInstanceListener() {
        return additionalArgs -> {
            if (additionalArgs.size() == 1 && additionalArgs.get(0).equals("stop")) {
                stop();
            }
        };
    }
}
