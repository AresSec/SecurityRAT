package org.appsec.securityrat.provider;

import java.util.List;
import java.util.Optional;
import org.appsec.securityrat.api.IdentifiableDtoProvider;
import org.appsec.securityrat.api.dto.rest.TrainingGeneratedSlideNode;
import org.springframework.stereotype.Service;

@Service
public class TrainingGeneratedSlideNodeProviderImpl
        implements IdentifiableDtoProvider<Long, TrainingGeneratedSlideNode> {
    
    @Override
    public List<TrainingGeneratedSlideNode> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<TrainingGeneratedSlideNode> findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TrainingGeneratedSlideNode save(TrainingGeneratedSlideNode dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TrainingGeneratedSlideNode> search(String query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
