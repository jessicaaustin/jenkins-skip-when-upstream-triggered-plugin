package jenkins.plugins;

import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.Job;
import hudson.model.JobProperty;
import hudson.model.JobPropertyDescriptor;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.export.Exported;

public class SkipWhenUpstreamTriggeredJobProperty extends JobProperty<AbstractProject<?, ?>> {

    public final boolean skipWhenUpstreamTriggered;

    @DataBoundConstructor
    public SkipWhenUpstreamTriggeredJobProperty(boolean skipWhenUpstreamTriggered) {
        this.skipWhenUpstreamTriggered = skipWhenUpstreamTriggered;
    }

    @Exported
    public boolean getSkipWhenUpstreamTriggered() {
        return skipWhenUpstreamTriggered;
    }

    @Override
    public boolean prebuild(AbstractBuild<?, ?> build, BuildListener listener) {
        return super.prebuild(build, listener);
    }


    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    @Extension
    public static final class DescriptorImpl extends JobPropertyDescriptor {
        @Override
        public String getDisplayName() {
            return "Skip build if any upstream projects are currently building or queued";
        }

        @Override
        public boolean isApplicable(Class<? extends Job> jobType) {
            return true;
        }

        @Override
        public SkipWhenUpstreamTriggeredJobProperty newInstance(StaplerRequest sr, JSONObject formData) throws hudson.model.Descriptor.FormException {
            return new SkipWhenUpstreamTriggeredJobProperty(sr.getParameter("skipWhenUpstreamTriggered") != null);
        }
    }
}

