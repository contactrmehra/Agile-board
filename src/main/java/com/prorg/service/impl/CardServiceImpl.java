package com.prorg.service.impl;

import com.prorg.dao.CardDao;
import com.prorg.helper.result.Response;
import com.prorg.helper.result.ValidationResponse;
import com.prorg.helper.validator.ModelValidator;
import com.prorg.model.Card;
import com.prorg.model.Swimlane;
import com.prorg.model.User;
import com.prorg.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
@Transactional
public class CardServiceImpl implements CardService {
    private final CardDao cardDao;
    private final ModelValidator validator;

    @Autowired
    public CardServiceImpl(CardDao cardDao, ModelValidator validator) {
        this.cardDao = cardDao;
        this.validator = validator;
    }

    @Override
    public Response createCard(String title, String description, Swimlane itsSwimlane, User creator) {
        Card cardToAdd = new Card();
        ArrayList<User> assignedUsers = new ArrayList<>();
        assignedUsers.add(creator);
        cardToAdd.setTitle(title)
                 .setDescription(description)
                 .setSwimlane(itsSwimlane)
                 .setAssignedUser(assignedUsers);
        ValidationResponse validationResponse = validator.validate(cardToAdd);
        if (!validationResponse.isValid()) {
            return Response.Failure(validationResponse.errors());
        }
        return cardDao.save(cardToAdd);
    }

    @Override
    public Response getCardById(int cardId) throws Exception {
        Response queryResponse = cardDao.findById(cardId);
        return Response.Success(queryResponse.data());
    }

    @Override
    public Response updateAssignedUsersOfCard(int cardId, ArrayList<User> assignedUsers) throws Exception {
        Response getCardResponse = cardDao.findById(cardId);
        if (getCardResponse.isSuccessful()) {
            Card cardToUpdate = (Card) getCardResponse.data();
            cardToUpdate.setAssignedUser(assignedUsers);
            cardDao.update(cardToUpdate);
            return Response.SuccessEmptyPayload();
        }
        return getCardResponse;
    }
}
