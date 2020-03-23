package org.appsec.securityrat.provider;

import java.util.List;
import java.util.Optional;
import org.appsec.securityrat.api.IdentifiableDtoProvider;
import org.appsec.securityrat.api.dto.rest.TrainingRequirementNode;
import org.springframework.stereotype.Service;

@Service
public class TrainingRequirementNodeProviderImpl
        implements IdentifiableDtoProvider<Long, TrainingRequirementNode> {

    @Override
    public List<TrainingRequirementNode> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<TrainingRequirementNode> findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TrainingRequirementNode save(TrainingRequirementNode dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TrainingRequirementNode> search(String query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
