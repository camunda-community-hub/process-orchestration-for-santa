import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class SendLetterToNaughtyChildJobHandler implements JobHandler {

    @Override
    public void handle(final JobClient client, final ActivatedJob job) {
        System.out.println(getClass().getSimpleName() + ": NOP");

        client.newCompleteCommand(job.getKey()).send().join();
    }
}
