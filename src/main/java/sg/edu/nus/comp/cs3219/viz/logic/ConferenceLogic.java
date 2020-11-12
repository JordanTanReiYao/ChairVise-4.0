package sg.edu.nus.comp.cs3219.viz.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;
import sg.edu.nus.comp.cs3219.viz.common.entity.Conference;
import sg.edu.nus.comp.cs3219.viz.storage.repository.ConferenceRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class ConferenceLogic {

    private static final Logger log = Logger.getLogger(ConferenceLogic.class.getSimpleName());

    private final ConferenceRepository conferenceRepository;

    @Autowired
    private MailSenderHelper mailSenderHelper;

    @Autowired
    private MailContentBuilder mailContentBuilder;
    
    public ConferenceLogic(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    public List<Conference> findAllForUser(UserInfo userInfo) {
        return conferenceRepository.findByCreatorIdentifier(userInfo.getUserEmail());
    }

    private static boolean isUpcomingDate(Date confDate, LocalDate now) {
        long diff = ChronoUnit.DAYS.between(now,
                confDate.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                );// rounded down
        return (0 < diff && diff < 3);
    }

    /**
     * Sends emails to users who have conferences within 3 days
     * Scheduled to run every day at 7:00 in the server's time zone (?)
     */
    //every 100 seconds, for testing
//    @Scheduled(fixedRate = 100000)
    @Scheduled(cron = "0 0 7 * * *")
    public void sendEmailNotifications() {
        if (conferenceRepository == null) { return; }
        List<Conference> all = conferenceRepository.findAll();
        Map<String, List<Conference>> upcoming_all = new HashMap<>();
        LocalDate now = LocalDate.now();
        for (Conference conf: all) {
            if (isUpcomingDate(conf.getDate(), now)) {
                if (!upcoming_all.containsKey(conf.getCreatorIdentifier())) {
                    upcoming_all.put(conf.getCreatorIdentifier(), new ArrayList<>());
                }
                upcoming_all.get(conf.getCreatorIdentifier()).add((conf));
            }
        }
        for (Map.Entry<String, List<Conference>> entry : upcoming_all.entrySet()) {
            String email = entry.getKey();
            List<Conference> upcoming = entry.getValue();
            if (upcoming.size() == 0) { continue; }
            // Send email
            log.info("Sending upcoming confs notification to user " + email);
            String message = mailContentBuilder.generateUpcomingConferencesContent(upcoming);
            mailSenderHelper.sendEmail(email, "chairvise@gmail.com", "Upcoming Conferences", message);
        }

        log.info("Upcoming confs email notifications scheduled task done");
    }

    public Optional<Conference> findById(Long id) {
        return conferenceRepository.findById(id);
    }

    public Conference saveForUser(Conference conference, UserInfo userInfo) {
        Conference newConference = new Conference();
        newConference.setName(conference.getName());
        newConference.setDescription(conference.getDescription());
        newConference.setDate(conference.getDate());
        newConference.setCreatorIdentifier(userInfo.getUserEmail());
        // Send email
        String message = mailContentBuilder.generateMailContent(conference, userInfo);
        mailSenderHelper.sendEmail(userInfo.getUserEmail(), "chairvise@gmail.com", "New Conference Added", message);
        return conferenceRepository.save(newConference);
    }

    public Conference updateConference(Conference oldConference, Conference newConference) {
        oldConference.setName(newConference.getName());
        oldConference.setDescription(newConference.getDescription());
        oldConference.setDate(newConference.getDate());
        return conferenceRepository.save(oldConference);
    }

    public void deleteById(Long id) {
        conferenceRepository.deleteById(id);
    }
}
