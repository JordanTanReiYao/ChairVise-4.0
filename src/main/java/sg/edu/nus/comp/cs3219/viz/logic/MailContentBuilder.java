
package sg.edu.nus.comp.cs3219.viz.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;
import sg.edu.nus.comp.cs3219.viz.common.entity.Conference;

@Service
public class MailContentBuilder {

    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String generateMailContent(Conference conference, UserInfo userInfo) {
        Context context = new Context();
        context.setVariable("userName", userInfo.getUserNickname());
        context.setVariable("conferenceName", conference.getName());
        context.setVariable("conferenceDescription", conference.getDescription());
        context.setVariable("conferenceDate", conference.getDate());
        
        return templateEngine.process("NewConference", context);
    }
}