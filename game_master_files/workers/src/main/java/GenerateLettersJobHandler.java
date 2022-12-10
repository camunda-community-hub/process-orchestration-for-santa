import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

import java.time.Duration;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class GenerateLettersJobHandler implements JobHandler {

    private static final Random RANDOM = new Random();

    private static final String[] NAMES = new String[] {
        "Liam",
        "Olivia",
        "Noah",
        "Emma",
        "Oliver",
        "Charlotte",
        "Elijah",
        "Amelia",
        "James",
        "Ava",
        "William",
        "Sophia",
        "Benjamin",
        "Isabella",
        "Lucas",
        "Mia",
        "Henry",
        "Evelyn",
        "Theodore",
        "Harper"
    };

    private static final String[] WISHES = new String[] {
        "a pony",
        "a fast toy car",
        "a ball",
        "lots of chocolate",
        "the Harry Potter book",
        "snow",
        "a PlayStation",
        "a bike",
        "two tennis rackets to play with my sister",
        "fancy sneakers",
        "a board game",
    };

    private final ZeebeClient zeebeClient;

    public GenerateLettersJobHandler(final ZeebeClient zeebeClient) {
        this.zeebeClient = zeebeClient;
    }

    @Override
    public void handle(final JobClient client, final ActivatedJob job) {
        final var variables = job.getVariablesAsMap();

        final int count = (Integer) variables.get("count");
        final String messageName = (String) variables.get("messageName");

        System.out.println(getClass().getSimpleName() + ": Generating " + count + " messages for " + messageName);

        for (int i = 0; i < count; i++) {

            final String letter = generateLetter();

            zeebeClient
                    .newPublishMessageCommand()
                    .messageName(messageName)
                    .correlationKey(UUID.randomUUID().toString())
                    .variables(Map.of("letter", letter))
                    .timeToLive(Duration.ofHours(3))
                    .send()
                    .join();
        }

        client.newCompleteCommand(job.getKey()).send().join();
    }

    private String generateLetter() {
        final var name = NAMES[RANDOM.nextInt(NAMES.length)];

        final var numberOfWishes = RANDOM.nextInt(7);

        final var wishes = new HashSet<String>();

        for (int i = 0; i <= numberOfWishes; i++) {
            wishes.add(WISHES[RANDOM.nextInt(WISHES.length)]);
        }

        return "Dear Santa, this is " + name + " my wishes are "
                + wishes.stream().collect(Collectors.joining(" and ")) + " Merry Christmas";
    }
}
