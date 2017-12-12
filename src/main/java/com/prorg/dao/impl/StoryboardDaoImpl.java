package com.prorg.dao.impl;

import com.prorg.dao.StoryboardDao;
import com.prorg.helper.result.Response;
import com.prorg.model.Storyboard;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StoryboardDaoImpl extends BaseDaoImpl implements StoryboardDao {

    @Autowired
    public StoryboardDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Storyboard> list() {
        return getCurrentSession().createQuery("from Storyboard").getResultList();
    }

    @Override
    public Response save(Storyboard storyboard) {
        return super.save(storyboard);
    }

    @Override
    public Response findById(int storyboardId) {
        return Response.Success(getCurrentSession().get(Storyboard.class, storyboardId));
    }

    @Override
    public Response deleteById(int storyboardId) {
        return super.delete(getCurrentSession().get(Storyboard.class, storyboardId));
    }

    @Override
    public Response update(Storyboard storyboard) {
        return super.update(storyboard);
    }
}