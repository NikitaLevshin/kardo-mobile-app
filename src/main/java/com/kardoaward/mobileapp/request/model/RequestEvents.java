package com.kardoaward.mobileapp.request.model;

import com.kardoaward.mobileapp.events.model.Event;
import com.kardoaward.mobileapp.status.AdminEventStatus;
import com.kardoaward.mobileapp.status.UserEventStatus;
import com.kardoaward.mobileapp.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Builder
@Table
@AllArgsConstructor
@NoArgsConstructor
public class RequestEvents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "requester_id", referencedColumnName = "id")
    private User requester;
    @Column(name = "result")
    private String result;
    @OneToMany(mappedBy = "requestEvent", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<RequestStage> stages;
    @Column(name = "place")
    private Integer place;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_to_user")
    private UserEventStatus statusToUser;
    @Column(name = "level")
    private String level;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AdminEventStatus status;


    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        RequestEvents requestEvents = (RequestEvents) object;
        return getId() != null && Objects.equals(getId(), requestEvents.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}