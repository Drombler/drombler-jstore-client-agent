package org.drombler.jstore.client.agent.startup;

import org.drombler.commons.client.startup.main.ApplicationInstanceListener;
import org.drombler.commons.client.startup.main.DromblerClientStarter;
import org.drombler.commons.client.startup.main.cli.CommandLineArgs;

import java.io.IOException;


/**
 * @author puce
 */
public class JStoreClientAgent extends DromblerClientStarter<JStoreClientAgentConfiguration> {

    public static void main(String[] args) throws Exception {
        CommandLineArgs commandLineArgs = CommandLineArgs.parseCommandLineArgs(args);
        JStoreClientAgentConfiguration configuration = new JStoreClientAgentConfiguration(commandLineArgs);
        JStoreClientAgent application = new JStoreClientAgent(configuration);

        if (application.init()) {
            application.start();
        }
        Thread.sleep(100000);
    }

    public JStoreClientAgent(JStoreClientAgentConfiguration configuration) throws IOException {
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
