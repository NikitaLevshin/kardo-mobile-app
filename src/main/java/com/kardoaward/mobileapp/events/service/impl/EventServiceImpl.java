package com.kardoaward.mobileapp.events.service.impl;

import com.kardoaward.mobileapp.events.dto.request.CreateEventDtoRequest;
import com.kardoaward.mobileapp.events.dto.request.UpdateEventDtoRequest;
import com.kardoaward.mobileapp.events.dto.response.EventFullDtoResponse;
import com.kardoaward.mobileapp.events.dto.response.EventNameDtoResponse;
import com.kardoaward.mobileapp.events.dto.response.EventToEpicDtoResponse;
import com.kardoaward.mobileapp.events.mapper.EventMapper;
import com.kardoaward.mobileapp.events.model.Event;
import com.kardoaward.mobileapp.events.repository.EventRepository;
import com.kardoaward.mobileapp.events.service.EventService;
import com.kardoaward.mobileapp.exceptions.LocalDateRequestException;
import com.kardoaward.mobileapp.exceptions.NullRequestException;
import com.kardoaward.mobileapp.stage.repository.StageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final StageRepository stageRepository;

    @Override
    @Transactional
    public void create(CreateEventDtoRequest eventDto) {
        isDate(eventDto.getEndDate(), eventDto.getStartDate());
        eventRepository.save(EventMapper.create(eventDto));
    }

    @Override
    @Transactional
    public void update(UpdateEventDtoRequest eventDto, Long eventId) {
        isUpdate(eventDto, eventId);
        eventRepository.save(EventMapper.update(eventDto, findById(eventId)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        eventRepository.delete(findById(id));
    }

    @Override
    public List<EventNameDtoResponse> findAllName() {
        return EventMapper.mapAllNames(eventRepository.findAll());
    }

    @Override
    public EventFullDtoResponse findByIdEventFullDto(Long id) {
        return EventMapper.findFullDto(findById(id));

    }

    @Override
    public List<EventFullDtoResponse> findAllEventFullDto() {
        return EventMapper.findAllFullDto(eventRepository.findAll());
    }

    @Override
    public List<EventToEpicDtoResponse> findAllToEpic() {
        return EventMapper.mapAllToEpic(eventRepository.findAll());
    }

    private Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(
                () -> new NullRequestException("Event with id " + id + " not found")
        );
    }

    private void isUpdate(final UpdateEventDtoRequest updateEvent, Long eventId) {
        if ((updateEvent.getStartDate() == null || updateEvent.getStartDate().isEmpty()) &&
        (updateEvent.getEndDate() == null || updateEvent.getEndDate().isEmpty())) {
            return;
        }
        DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Event event = findById(eventId);
        if (isNullDate(updateEvent.getStartDate())) {
            event.setStart(LocalDate.parse(updateEvent.getStartDate(), DTF));
        }
        if (isNullDate(updateEvent.getEndDate())) {
            event.setEnd(LocalDate.parse(updateEvent.getEndDate(), DTF));
        }
        isDate(event.getEnd(), event.getStart());
        if (!stageRepository.findByEvent_IdAndStartBeforeOrEndAfter
                (eventId, event.getStart(), event.getEnd()).isEmpty()) {
            throw new LocalDateRequestException("The time of the event goes beyond the time frame of the stages");
        }
    }

    private void isDate(final LocalDate end, final LocalDate start) {
        if (end.isBefore(start)) {
            throw new LocalDateRequestException("the start date of the event must be later today," +
                    " the end date of the event must be later than the start date of the event");
        }
    }

    private Boolean isNullDate(final String date) {
        return date != null && !date.isEmpty();
    }

}
