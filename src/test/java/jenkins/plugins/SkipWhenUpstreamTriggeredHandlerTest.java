package jenkins.plugins;

import hudson.model.AbstractProject;
import hudson.model.Queue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class SkipWhenUpstreamTriggeredHandlerTest {
    private SkipWhenUpstreamTriggeredHandler handler;

    @Before
    public void setup() {
        handler = new SkipWhenUpstreamTriggeredHandler();
    }

    @Test
    public void nonProjectTasksShouldBeIgnored() {
        Queue.Task queueTask = mock(Queue.Task.class);

        assertTrue(handler.shouldSchedule(queueTask, null));
        verifyZeroInteractions(queueTask);
    }

    @Test
    public void projectsWithoutPropertyShouldBeIgnored() {
        AbstractProject project = mock(AbstractProject.class);

        assertTrue(handler.shouldSchedule(project, null));
        verify(project).getProperty(SkipWhenUpstreamTriggeredJobProperty.class);
        verifyNoMoreInteractions(project);
    }

}
