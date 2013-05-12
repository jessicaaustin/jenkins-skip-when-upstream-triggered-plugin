package jenkins.plugins;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.Queue.QueueDecisionHandler;
import hudson.model.Queue.Task;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Extension
public class SkipWhenUpstreamTriggeredHandler extends QueueDecisionHandler {
    private final Logger LOGGER = Logger.getLogger(Logger.class.getName());

    @Override
    public boolean shouldSchedule(Task p, List<Action> actions) {
        if (p instanceof AbstractProject) {
            if (((AbstractProject) p).getProperty(SkipWhenUpstreamTriggeredJobProperty.class) != null) {

                // If any upstream projects are currently queued or building, skip scheduling
                Set<AbstractProject> upstreamProjects = ((AbstractProject) p).getTransitiveUpstreamProjects();
                for (AbstractProject upstreamProject : upstreamProjects) {
                    if (upstreamProject.isBuilding()) {
                        return false;
                    }
                    if (upstreamProject.isInQueue()) {
                        return false;
                    }
                }

            }
        }
        return true;
    }
}
