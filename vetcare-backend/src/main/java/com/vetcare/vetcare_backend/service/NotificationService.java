package com.vetcare.vetcare_backend.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.vetcare.vetcare_backend.entity.Prescription;
import com.vetcare.vetcare_backend.entity.Visit;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.whatsapp.from}")
    private String fromNumber;

    // @PostConstruct runs once when Spring starts up
    // It initializes the Twilio SDK with your credentials
    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    public void sendVisitSummaryWhatsApp(Visit visit) {
        try {
            String ownerPhone = visit.getPet().getOwner().getPhone();
            String ownerName = visit.getPet().getOwner().getName();
            String petName = visit.getPet().getName();

            String message = buildVisitMessage(ownerName, petName, visit);

            // Send WhatsApp message via Twilio
            // Indian numbers: +91 prefix
            Message.creator(
                    new PhoneNumber("whatsapp:+91" + ownerPhone),
                    new PhoneNumber(fromNumber),
                    message
            ).create();

            System.out.println("✅ WhatsApp sent to " + ownerPhone);

        } catch (Exception e) {
            // Log error but don't crash — visit is already saved
            System.out.println("❌ WhatsApp error: " + e.getMessage());
        }
    }

    private String buildVisitMessage(String ownerName,
                                      String petName,
                                      Visit visit) {
        StringBuilder sb = new StringBuilder();

        sb.append("🐾 *VetCare - Visit Summary*\n\n");
        sb.append("Hi ").append(ownerName).append("! ");
        sb.append(petName).append("'s visit summary:\n\n");

        if (visit.getDiagnosis() != null) {
            sb.append("*Diagnosis:* ")
              .append(visit.getDiagnosis())
              .append("\n\n");
        }

        List<Prescription> prescriptions = visit.getPrescriptions();
        if (prescriptions != null && !prescriptions.isEmpty()) {
            sb.append("*Medicines Prescribed:*\n");
            for (Prescription p : prescriptions) {
                sb.append("• ").append(p.getMedicineName());
                if (p.getDosage() != null)
                    sb.append(" - ").append(p.getDosage());
                if (p.getDuration() != null)
                    sb.append(", ").append(p.getDuration());
                if (p.getInstructions() != null)
                    sb.append(" (").append(p.getInstructions()).append(")");
                sb.append("\n");
            }
            sb.append("\n");
        }

        if (visit.getNextVisitDate() != null) {
            sb.append("*Next Visit:* ")
              .append(visit.getNextVisitDate())
              .append("\n\n");
        }

        sb.append("_Sent via VetCare_ 🐾");

        return sb.toString();
    }
}