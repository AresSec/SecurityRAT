package org.appsec.securityrat.provider;

import com.google.common.base.Preconditions;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.inject.Inject;
import org.appsec.securityrat.api.dto.training.TrainingBranchNodeDto;
import org.appsec.securityrat.api.dto.training.TrainingCategoryNodeDto;
import org.appsec.securityrat.api.dto.training.TrainingCustomSlideNodeDto;
import org.appsec.securityrat.api.dto.training.TrainingGeneratedSlideNodeDto;
import org.appsec.securityrat.api.dto.training.TrainingRequirementNodeDto;
import org.appsec.securityrat.api.dto.training.TrainingTreeNodeDto;
import org.appsec.securityrat.api.dto.training.TrainingTreeStatusDto;
import org.appsec.securityrat.api.provider.TrainingNodeProvider;
import org.appsec.securityrat.domain.TrainingBranchNode;
import org.appsec.securityrat.domain.TrainingCategoryNode;
import org.appsec.securityrat.domain.TrainingCustomSlideNode;
import org.appsec.securityrat.domain.TrainingGeneratedSlideNode;
import org.appsec.securityrat.domain.TrainingRequirementNode;
import org.appsec.securityrat.domain.TrainingTreeNode;
import org.appsec.securityrat.provider.mapper.training.TrainingBranchNodeMapper;
import org.appsec.securityrat.provider.mapper.training.TrainingCategoryNodeMapper;
import org.appsec.securityrat.provider.mapper.training.TrainingCustomSlideNodeMapper;
import org.appsec.securityrat.provider.mapper.training.TrainingGeneratedSlideNodeMapper;
import org.appsec.securityrat.provider.mapper.training.TrainingRequirementNodeMapper;
import org.appsec.securityrat.provider.mapper.training.TrainingTreeNodeMapper;
import org.appsec.securityrat.repository.TrainingBranchNodeRepository;
import org.appsec.securityrat.repository.TrainingCategoryNodeRepository;
import org.appsec.securityrat.repository.TrainingCustomSlideNodeRepository;
import org.appsec.securityrat.repository.TrainingGeneratedSlideNodeRepository;
import org.appsec.securityrat.repository.TrainingRequirementNodeRepository;
import org.appsec.securityrat.repository.TrainingTreeNodeRepository;
import org.appsec.securityrat.repository.search.TrainingBranchNodeSearchRepository;
import org.appsec.securityrat.repository.search.TrainingCategoryNodeSearchRepository;
import org.appsec.securityrat.repository.search.TrainingCustomSlideNodeSearchRepository;
import org.appsec.securityrat.repository.search.TrainingGeneratedSlideNodeSearchRepository;
import org.appsec.securityrat.repository.search.TrainingRequirementNodeSearchRepository;
import org.appsec.securityrat.repository.search.TrainingTreeNodeSearchRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

@Service
public class TrainingNodeProviderImpl implements TrainingNodeProvider {
    @Inject
    private TrainingBranchNodeRepository branchNodeRepo;
    
    @Inject
    private TrainingCategoryNodeRepository categoryNodeRepo;
    
    @Inject
    private TrainingCustomSlideNodeRepository customSlideNodeRepo;
    
    @Inject
    private TrainingGeneratedSlideNodeRepository generatedSlideNodeRepo;
    
    @Inject
    private TrainingRequirementNodeRepository requirementNodeRepo;
    
    @Inject
    private TrainingTreeNodeRepository treeNodeRepo;
    
    @Inject
    private TrainingBranchNodeSearchRepository branchNodeSearchRepo;
    
    @Inject
    private TrainingCategoryNodeSearchRepository categoryNodeSearchRepo;
    
    @Inject
    private TrainingCustomSlideNodeSearchRepository customSlideNodeSearchRepo;
    
    @Inject
    private TrainingGeneratedSlideNodeSearchRepository generatedSlideNodeSearchRepo;
    
    @Inject
    private TrainingRequirementNodeSearchRepository requirementNodeSearchRepo;
    
    @Inject
    private TrainingTreeNodeSearchRepository treeNodeSearchRepo;
    
    @Inject
    private TrainingBranchNodeMapper branchNodeMapper;
    
    @Inject
    private TrainingCategoryNodeMapper categoryNodeMapper;
    
    @Inject
    private TrainingCustomSlideNodeMapper customSlideNodeMapper;
    
    @Inject
    private TrainingGeneratedSlideNodeMapper generatedSlideNodeMapper;
    
    @Inject
    private TrainingRequirementNodeMapper requirementNodeMapper;
    
    @Inject
    private TrainingTreeNodeMapper treeNodeMapper;
    
    @Override
    public boolean createBranchNode(TrainingBranchNodeDto dto) {
        Preconditions.checkNotNull(dto);
        
        TrainingBranchNode entity = this.branchNodeMapper.toEntity(dto);
        
        if (entity.getId() != null) {
            return false;
        }
        
        this.branchNodeRepo.save(entity);
        this.branchNodeSearchRepo.save(entity);
        
        dto.setId(entity.getId());
        return true;
    }

    @Override
    public boolean createCategoryNode(TrainingCategoryNodeDto dto) {
        Preconditions.checkNotNull(dto);
        
        TrainingCategoryNode entity = this.categoryNodeMapper.toEntity(dto);
        
        if (entity.getId() != null) {
            return false;
        }
        
        this.categoryNodeRepo.save(entity);
        this.categoryNodeSearchRepo.save(entity);
        
        dto.setId(entity.getId());
        return true;
    }

    @Override
    public boolean createCustomSlideNode(TrainingCustomSlideNodeDto dto) {
        Preconditions.checkNotNull(dto);
        
        TrainingCustomSlideNode entity =
                this.customSlideNodeMapper.toEntity(dto);
        
        if (entity.getId() != null) {
            return false;
        }
        
        this.customSlideNodeRepo.save(entity);
        this.customSlideNodeSearchRepo.save(entity);
        
        dto.setId(entity.getId());
        return true;
    }

    @Override
    public boolean createGeneratedSlideNode(TrainingGeneratedSlideNodeDto dto) {
        Preconditions.checkNotNull(dto);
        
        TrainingGeneratedSlideNode entity =
                this.generatedSlideNodeMapper.toEntity(dto);
        
        if (entity.getId() != null) {
            return false;
        }
        
        this.generatedSlideNodeRepo.save(entity);
        this.generatedSlideNodeSearchRepo.save(entity);
        
        dto.setId(entity.getId());
        return true;
    }

    @Override
    public boolean createRequirementNode(TrainingRequirementNodeDto dto) {
        Preconditions.checkNotNull(dto);
        
        TrainingRequirementNode entity =
                this.requirementNodeMapper.toEntity(dto);
        
        if (entity.getId() != null) {
            return false;
        }
        
        this.requirementNodeRepo.save(entity);
        this.requirementNodeSearchRepo.save(entity);
        
        dto.setId(entity.getId());
        return true;
    }

    @Override
    public boolean createTreeNode(TrainingTreeNodeDto dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateBranchNode(TrainingBranchNodeDto dto) {
        Preconditions.checkNotNull(dto);
        
        TrainingBranchNode entity = this.branchNodeMapper.toEntity(dto);
        
        if (entity.getId() == null) {
            return false;
        }
        
        this.branchNodeRepo.save(entity);
        this.branchNodeSearchRepo.save(entity);
        
        return true;
    }

    @Override
    public boolean updateCategoryNode(TrainingCategoryNodeDto dto) {
        Preconditions.checkNotNull(dto);
        
        TrainingCategoryNode entity = this.categoryNodeMapper.toEntity(dto);
        
        if (entity.getId() == null) {
            return false;
        }
        
        this.categoryNodeRepo.save(entity);
        this.categoryNodeSearchRepo.save(entity);
        
        return true;
    }

    @Override
    public boolean updateCustomSlideNode(TrainingCustomSlideNodeDto dto) {
        Preconditions.checkNotNull(dto);
        
        TrainingCustomSlideNode entity =
                this.customSlideNodeMapper.toEntity(dto);
        
        if (entity.getId() == null) {
            return false;
        }
        
        this.customSlideNodeRepo.save(entity);
        this.customSlideNodeSearchRepo.save(entity);
        
        return true;
    }

    @Override
    public boolean updateGeneratedSlideNode(TrainingGeneratedSlideNodeDto dto) {
        Preconditions.checkNotNull(dto);
        
        TrainingGeneratedSlideNode entity =
                this.generatedSlideNodeMapper.toEntity(dto);
        
        if (entity.getId() == null) {
            return false;
        }
        
        this.generatedSlideNodeRepo.save(entity);
        this.generatedSlideNodeSearchRepo.save(entity);
        
        return true;
    }

    @Override
    public boolean updateRequirementNode(TrainingRequirementNodeDto dto) {
        Preconditions.checkNotNull(dto);
        
        TrainingRequirementNode entity =
                this.requirementNodeMapper.toEntity(dto);
        
        if (entity.getId() == null) {
            return false;
        }
        
        this.requirementNodeRepo.save(entity);
        this.requirementNodeSearchRepo.save(entity);
        
        return true;
    }

    @Override
    public boolean updateTreeNode(TrainingTreeNodeDto dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteBranchNode(Long id) {
        Preconditions.checkNotNull(id);
        
        TrainingBranchNode entity =
                this.branchNodeRepo.findById(id)
                        .orElse(null);
        
        if (entity == null) {
            return false;
        }
        
        this.branchNodeRepo.delete(entity);
        this.branchNodeSearchRepo.delete(entity);
        
        return true;
    }

    @Override
    public boolean deleteCategoryNode(Long id) {
        Preconditions.checkNotNull(id);
        
        TrainingCategoryNode entity =
                this.categoryNodeRepo.findById(id)
                        .orElse(null);
        
        if (entity == null) {
            return false;
        }
        
        this.categoryNodeRepo.delete(entity);
        this.categoryNodeSearchRepo.delete(entity);
        
        return true;
    }

    @Override
    public boolean deleteCustomSlideNode(Long id) {
        Preconditions.checkNotNull(id);
        
        TrainingCustomSlideNode entity =
                this.customSlideNodeRepo.findById(id)
                        .orElse(null);
        
        if (entity == null) {
            return false;
        }
        
        this.customSlideNodeRepo.delete(entity);
        this.customSlideNodeSearchRepo.delete(entity);
        
        return true;
    }

    @Override
    public boolean deleteGeneratedSlideNode(Long id) {
        Preconditions.checkNotNull(id);
        
        TrainingGeneratedSlideNode entity =
                this.generatedSlideNodeRepo.findById(id)
                        .orElse(null);
        
        if (entity == null) {
            return false;
        }
        
        this.generatedSlideNodeRepo.delete(entity);
        this.generatedSlideNodeSearchRepo.delete(entity);
        
        return true;
    }

    @Override
    public boolean deleteRequirementNode(Long id) {
        Preconditions.checkNotNull(id);
        
        TrainingRequirementNode entity =
                this.requirementNodeRepo.findById(id)
                        .orElse(null);
        
        if (entity == null) {
            return false;
        }
        
        this.requirementNodeRepo.delete(entity);
        this.requirementNodeSearchRepo.delete(entity);
        
        return true;
    }

    @Override
    public boolean deleteTreeNode(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TrainingBranchNodeDto findBranchNode(Long id) {
        Preconditions.checkNotNull(id);
        
        return this.branchNodeRepo.findById(id)
                .map(this.branchNodeMapper::toDto)
                .orElse(null);
    }

    @Override
    public TrainingCategoryNodeDto findCategoryNode(Long id) {
        Preconditions.checkNotNull(id);
        
        return this.categoryNodeRepo.findById(id)
                .map(this.categoryNodeMapper::toDto)
                .orElse(null);
    }

    @Override
    public TrainingCustomSlideNodeDto findCustomSlideNode(Long id) {
        Preconditions.checkNotNull(id);
        
        return this.customSlideNodeRepo.findById(id)
                .map(this.customSlideNodeMapper::toDto)
                .orElse(null);
    }

    @Override
    public TrainingGeneratedSlideNodeDto findGeneratedSlideNode(Long id) {
        Preconditions.checkNotNull(id);
        
        return this.generatedSlideNodeRepo.findById(id)
                .map(this.generatedSlideNodeMapper::toDto)
                .orElse(null);
    }

    @Override
    public TrainingRequirementNodeDto findRequirementNode(Long id) {
        Preconditions.checkNotNull(id);
        
        return this.requirementNodeRepo.findById(id)
                .map(this.requirementNodeMapper::toDto)
                .orElse(null);
    }

    @Override
    public Set<TrainingBranchNodeDto> findAllBranchNodes() {
        return this.branchNodeRepo.findAll()
                .stream()
                .map(this.branchNodeMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<TrainingCategoryNodeDto> findAllCategoryNodes() {
        return this.categoryNodeRepo.findAll()
                .stream()
                .map(this.categoryNodeMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<TrainingCustomSlideNodeDto> findAllCustomSlideNodes() {
        return this.customSlideNodeRepo.findAll()
                .stream()
                .map(this.customSlideNodeMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<TrainingGeneratedSlideNodeDto> findAllGeneratedSlideNodes() {
        return this.generatedSlideNodeRepo.findAll()
                .stream()
                .map(this.generatedSlideNodeMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<TrainingRequirementNodeDto> findAllRequirementNodes() {
        return this.requirementNodeRepo.findAll()
                .stream()
                .map(this.requirementNodeMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public List<TrainingTreeNodeDto> findAllTreeNodes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TrainingBranchNodeDto> searchBranchNodes(String query) {
        Preconditions.checkNotNull(query);
        
        return StreamSupport.stream(this.branchNodeSearchRepo.search(
                QueryBuilders.queryStringQuery(query)).spliterator(),
                false)
                .map(this.branchNodeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingCategoryNodeDto> searchCategoryNodes(String query) {
        Preconditions.checkNotNull(query);
        
        return StreamSupport.stream(this.categoryNodeSearchRepo.search(
                QueryBuilders.queryStringQuery(query)).spliterator(),
                false)
                .map(this.categoryNodeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingCustomSlideNodeDto> searchCustomSlideNodes(String query) {
        Preconditions.checkNotNull(query);
        
        return StreamSupport.stream(this.customSlideNodeSearchRepo.search(
                QueryBuilders.queryStringQuery(query)).spliterator(),
                false)
                .map(this.customSlideNodeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingGeneratedSlideNodeDto> searchGeneratedSlideNodes(String query) {
        Preconditions.checkNotNull(query);
        
        return StreamSupport.stream(this.generatedSlideNodeSearchRepo.search(
                QueryBuilders.queryStringQuery(query)).spliterator(),
                false)
                .map(this.generatedSlideNodeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingRequirementNodeDto> searchRequirementNodes(String query) {
        Preconditions.checkNotNull(query);
        
        return StreamSupport.stream(this.requirementNodeSearchRepo.search(
                QueryBuilders.queryStringQuery(query)).spliterator(),
                false)
                .map(this.requirementNodeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingTreeNodeDto> searchTreeNodes(String query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TrainingBranchNodeDto findBranchNodeByTreeNode(Long id) {
        Preconditions.checkNotNull(id);
        
        TrainingTreeNode treeNode = this.treeNodeRepo.findById(id)
                .orElse(null);
        
        if (treeNode == null) {
            return null;
        }
        
        TrainingBranchNode targetNode =
                this.branchNodeRepo.getTrainingBranchNodeByTrainingTreeNode(
                        treeNode);
        
        if (targetNode == null) {
            return null;
        }
        
        return this.branchNodeMapper.toDto(targetNode);
    }

    @Override
    public TrainingCategoryNodeDto findCategoryNodeByTreeNode(Long id) {
        Preconditions.checkNotNull(id);
        
        TrainingTreeNode treeNode = this.treeNodeRepo.findById(id)
                .orElse(null);
        
        if (treeNode == null) {
            return null;
        }
        
        TrainingCategoryNode targetNode =
                this.categoryNodeRepo.getTrainingCategoryNodeByTrainingTreeNode(
                        treeNode);
        
        if (targetNode == null) {
            return null;
        }
        
        return this.categoryNodeMapper.toDto(targetNode);
    }

    @Override
    public TrainingCustomSlideNodeDto findCustomSlideNodeByTreeNode(Long id) {
        Preconditions.checkNotNull(id);
        
        TrainingTreeNode treeNode = this.treeNodeRepo.findById(id)
                .orElse(null);
        
        if (treeNode == null) {
            return null;
        }
        
        TrainingCustomSlideNode targetNode =
                this.customSlideNodeRepo.getTrainingCustomSlideNodeByTrainingTreeNode(
                        treeNode);
        
        if (targetNode == null) {
            return null;
        }
        
        return this.customSlideNodeMapper.toDto(targetNode);
    }

    @Override
    public TrainingGeneratedSlideNodeDto findGeneratedSlideNodeByTreeNode(Long id) {
        Preconditions.checkNotNull(id);
        
        TrainingTreeNode treeNode = this.treeNodeRepo.findById(id)
                .orElse(null);
        
        if (treeNode == null) {
            return null;
        }
        
        TrainingGeneratedSlideNode targetNode =
                this.generatedSlideNodeRepo.getTrainingGeneratedSlideNodeByTrainingTreeNode(
                        treeNode);
        
        if (targetNode == null) {
            return null;
        }
        
        return this.generatedSlideNodeMapper.toDto(targetNode);
    }

    @Override
    public TrainingRequirementNodeDto findRequirementNodeByTreeNode(Long id) {
        Preconditions.checkNotNull(id);
        
        TrainingTreeNode treeNode = this.treeNodeRepo.findById(id)
                .orElse(null);
        
        if (treeNode == null) {
            return null;
        }
        
        TrainingRequirementNode targetNode =
                this.requirementNodeRepo.getTrainingRequirementNodeByTrainingTreeNode(
                        treeNode);
        
        if (targetNode == null) {
            return null;
        }
        
        return this.requirementNodeMapper.toDto(targetNode);
    }

    @Override
    public TrainingTreeStatusDto updateTreeReadOnly(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TrainingTreeStatusDto updateTree(TrainingTreeNodeDto idWrapped) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TrainingTreeNodeDto getSubTreeById(Long id, boolean prepareContent, boolean includeIds, String parentName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TrainingTreeNodeDto getTrainingRoot(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TrainingTreeNodeDto> getChildrenOf(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
