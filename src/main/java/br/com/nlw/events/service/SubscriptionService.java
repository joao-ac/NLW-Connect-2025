package br.com.nlw.events.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nlw.events.dto.SubscriptionResponse;
import br.com.nlw.events.exception.EventNotFoundException;
import br.com.nlw.events.exception.SubscriptionConflictException;
import br.com.nlw.events.exception.UserIndicadorNotFoundException;
import br.com.nlw.events.model.Event;
import br.com.nlw.events.model.Subscription;
import br.com.nlw.events.model.User;
import br.com.nlw.events.repo.EventRepo;
import br.com.nlw.events.repo.SubscriptionRepo;
import br.com.nlw.events.repo.UserRepo;

@Service
public class SubscriptionService {
	
	@Autowired
	private EventRepo evtRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private SubscriptionRepo subRepo;
	
	public SubscriptionResponse createNewSubscription(String eventName, User user, Integer userId) {
		
		// recuperar o evento pelo nome
		Event evt = evtRepo.findByPrettyName(eventName);
		if (evt == null) {
			throw new EventNotFoundException("Evento " + eventName + " nao existe");
		}
		
		User userRec = userRepo.findByEmail(user.getEmail());
		if (userRec == null) {
			userRec = userRepo.save(user);
		}
		
		User indicador = null;
		if (userId != null) {
			indicador = userRepo.findById(userId).orElse(null);
			if (indicador == null) {
				throw new UserIndicadorNotFoundException("Usuario " + userId + " indicador nao existe");
			}
		}
		
		Subscription subs = new Subscription();
		subs.setEvent(evt);
		subs.setSubscriber(userRec);
		subs.setIndication(indicador);
		
		Subscription tmpSub = subRepo.findByEventAndSubscriber(evt, userRec);
		if (tmpSub != null) {
			throw new SubscriptionConflictException("Ja existe inscricao para o usuario " + userRec.getName() + " no evento " + evt.getTitle());
		}
		
		Subscription res = subRepo.save(subs);
		return new SubscriptionResponse(res.getSubscriptionNumber(), "http://codecraft.com/" + res.getEvent().getPrettyName() + "/" + res.getSubscriber().getId());
	}
	
}
