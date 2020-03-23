package org.appsec.securityrat.provider;

import java.util.List;
import java.util.Optional;
import org.appsec.securityrat.api.IdentifiableDtoProvider;
import org.appsec.securityrat.api.dto.rest.Training;
import org.springframework.stereotype.Service;

@Service
public class TrainingProviderImpl
        implements IdentifiableDtoProvider<Long, Training> {
    
    @Override
    public List<Training> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<Training> findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Training save(Training dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Training> search(String query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
