import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

import java.util.concurrent.CountDownLatch;

public class Workers {
    public static void main(final String[] args) throws InterruptedException {
        System.out.println("Starting workers...");

        final String zeebeAddress = null;
        final String clientId = null;
        final String clientSecret = null;
        final String authorizationServerUrl = null;
        final String audience = null;

        System.out.println("Connecting to " + zeebeAddress);
        final ZeebeClient client =
                createZeebeClient(zeebeAddress, clientId, clientSecret, authorizationServerUrl, audience);

        final var topology = client.newTopologyRequest().send().join();

        System.out.println(topology);

        System.out.println("Registering worker for jobType: generate_letters_job");
        final JobWorker generateLettersJob = client.newWorker()
                .jobType("generate_letters_job")
                .handler(new GenerateLettersJobHandler(client))
                .open();

        System.out.println("Registering worker for jobType: send_letter_to_naughty_child");
        final JobWorker sendLetterToNaughtyChildJob = client.newWorker()
                .jobType("send_letter_to_naughty_child")
                .handler(new SendLetterToNaughtyChildJobHandler())
                .open();

        System.out.println("Registering worker for jobType: pack_present");
        final JobWorker packPresentJob = client.newWorker()
                .jobType("pack_present")
                .handler(new PackPresentJobHandler())
                .open();

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            generateLettersJob.close();
            sendLetterToNaughtyChildJob.close();
            packPresentJob.close();

            System.out.println("Closing client connected to " + zeebeAddress);
            client.close();

            System.out.println("Shutdown Complete");
            countDownLatch.countDown();
        }));

        countDownLatch.await();
    }

    private static ZeebeClient createZeebeClient(
            final String gatewayAddress,
            final String clientId,
            final String clientSecret,
            final String authorizationServerUrl,
            final String audience) {
        final OAuthCredentialsProvider provider = new OAuthCredentialsProviderBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .authorizationServerUrl(authorizationServerUrl)
                .audience(audience)
                .build();
        return ZeebeClient.newClientBuilder()
                .gatewayAddress(gatewayAddress)
                .credentialsProvider(provider)
                .build();
    }
}
