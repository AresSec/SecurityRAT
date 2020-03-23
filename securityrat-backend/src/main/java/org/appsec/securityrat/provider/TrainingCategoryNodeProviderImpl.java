package org.appsec.securityrat.provider;

import java.util.List;
import java.util.Optional;
import org.appsec.securityrat.api.IdentifiableDtoProvider;
import org.appsec.securityrat.api.dto.rest.TrainingCategoryNode;
import org.springframework.stereotype.Service;

@Service
public class TrainingCategoryNodeProviderImpl
        implements IdentifiableDtoProvider<Long, TrainingCategoryNode> {
    
    @Override
    public List<TrainingCategoryNode> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<TrainingCategoryNode> findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TrainingCategoryNode save(TrainingCategoryNode dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TrainingCategoryNode> search(String query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
