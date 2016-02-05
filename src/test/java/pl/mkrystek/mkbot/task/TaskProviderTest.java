package pl.mkrystek.mkbot.task;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import pl.mkrystek.mkbot.task.impl.EchoTask;
import pl.mkrystek.mkbot.task.impl.HelloTask;
import pl.mkrystek.mkbot.task.impl.HelpTask;
import pl.mkrystek.mkbot.task.impl.TimeTask;

@RunWith(MockitoJUnitRunner.class)
public class TaskProviderTest {

    private TaskProvider taskProvider;

    @Test
    public void shouldNotLoadNonExistingTask() {
        //given
        setupTaskProvider("NoSuchTask");

        //when
        List<ReplyTask> tasks = taskProvider.getReplyTasks();

        //then
        assertThat(tasks).isEmpty();
    }

    @Test
    public void shouldProperlyLoadExistingTask() {
        //given
        setupTaskProvider("EchoTask");

        //when
        List<ReplyTask> tasks = taskProvider.getReplyTasks();

        //then
        assertThat(tasks).hasSize(1).hasAtLeastOneElementOfType(EchoTask.class);
    }

    @Test
    public void shouldProperlyLoadMultipleExistingTasks() {
        //given
        setupTaskProvider("EchoTask HelloTask HelpTask TimeTask");

        //when
        List<ReplyTask> tasks = taskProvider.getReplyTasks();

        //then
        assertThat(tasks).hasSize(4).hasAtLeastOneElementOfType(EchoTask.class).hasAtLeastOneElementOfType(HelloTask.class)
            .hasAtLeastOneElementOfType(HelpTask.class).hasAtLeastOneElementOfType(TimeTask.class);
    }

    @Test
    public void shouldIgnoreOneInvalidTaskName() {
        //given
        setupTaskProvider("EchoTask NoSuchTask HelpTask");

        //when
        List<ReplyTask> tasks = taskProvider.getReplyTasks();

        //then
        assertThat(tasks).hasSize(2).hasAtLeastOneElementOfType(EchoTask.class).hasAtLeastOneElementOfType(HelpTask.class);
    }

    private void setupTaskProvider(String replyTasks) {
        taskProvider = new TaskProvider();
        taskProvider.setReplyTasksToLoad(replyTasks);
    }
}