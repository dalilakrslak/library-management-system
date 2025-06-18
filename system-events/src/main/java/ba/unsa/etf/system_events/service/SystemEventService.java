package ba.unsa.etf.system_events.service;

import ba.unsa.etf.system_events.dao.model.SystemEvent;
import ba.unsa.etf.system_events.dao.repository.SystemEventRepository;
import ba.unsa.etf.systemevents.proto.EventRequest;
import ba.unsa.etf.systemevents.proto.EventResponse;
import ba.unsa.etf.systemevents.proto.SystemEventServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SystemEventService extends SystemEventServiceGrpc.SystemEventServiceImplBase {

    private SystemEventRepository systemEventRepository;

    @Override
    public void logEvent(EventRequest request, StreamObserver<EventResponse> responseObserver) {
        SystemEvent log = new SystemEvent();
        log.setTimestamp(LocalDateTime.parse(request.getTimestamp()));
        log.setMicroserviceName(request.getMicroservice());
        log.setUser(request.getUser());
        log.setActionType(request.getActionType());
        log.setResourceName(request.getResource());
        log.setResponseType(request.getResponseStatus());
        systemEventRepository.save(log);

        responseObserver.onNext(EventResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Event saved")
                .build());
        responseObserver.onCompleted();
    }
}
