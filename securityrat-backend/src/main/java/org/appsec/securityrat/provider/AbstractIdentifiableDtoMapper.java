package org.appsec.securityrat.provider;

import java.util.Objects;
import java.util.Optional;
import org.appsec.securityrat.api.dto.IdentifiableDto;

public abstract class AbstractIdentifiableDtoMapper<
            TId,
            TDto extends IdentifiableDto<TId>,
            TEntity>
        extends AbstractDtoMapper<TDto, TEntity> {
    /**
     * Creates or updates the passed <code>target</code> entity with the
     * information from the <code>dto</code> instance.
     * 
     * <p>
     * <b>Note:</b>
     * Please do not call this method directly as implementations are not
     * required to expect that the passed <code>dto</code> is <code>null</code>.
     * Please use
     * {@link #createOrUpdateEntityChecked(org.appsec.securityrat.api.dto.IdentifiableDto, java.lang.Object)}
     * instead.
     * 
     * @param dto The data transfer that provides new or updated information for
     *            the <code>target</code> entity.
     * 
     * @param target Either an instance of the <code>TEntity</code> class that
     *               is the target of the update, or <code>null</code>, if the
     *               passed <code>dto</code> provides information for a new,
     *               non-existing entity.
     *               In the later case, the implementation is responsible for
     *               setting up a new instance of <code>TEntity</code>.
     * 
     * @return The new or updated entity instance. If <code>target</code> is not
     *         <code>null</code> (and thus the <code>target</code> entity is
     *         only updated and NOT newly created), it is expected that the
     *         return value references the same object as <code>target</code>
     *         does.
     * 
     * @see #createOrUpdateEntityChecked(org.appsec.securityrat.api.dto.IdentifiableDto, java.lang.Object)
     */
    protected abstract TEntity createOrUpdateEntity(TDto dto, TEntity target);
    
    // NOTE: The following "getId(TEntity)" method is very basic and could be
    //       replaced with some reflection.
    //       Experience shows that reflection code is hard to maintain (due to
    //       the lack of checks at compile time) and can break very easily.
    
    /**
     * Returns the unique identifier of the passed <code>entity</code>.
     * 
     * @param entity The entity whose identifier is returned.
     * 
     * @return Either the identifier of <code>entity</code> or
     *         <code>null</code>, if the identifier is unknown (e.g. because the
     *         identity has not been stored yet).
     */
    protected abstract TId getId(TEntity entity);
    
    /**
     * Creates or updates the passed <code>target</code> entity with the
     * information from the <code>dto</code> instance.
     * 
     * @param dto The data transfer that provides new or updated information for
     *            the <code>target</code> entity.
     * 
     * @param target Either an instance of the <code>TEntity</code> class that
     *               is the target of the update, or <code>null</code>, if the
     *               passed <code>dto</code> provides information for a new,
     *               non-existing entity.
     * 
     * @return The new or updated entity instance.
     */
    public TEntity createOrUpdateEntityChecked(TDto dto, TEntity target) {
        if (dto == null) {
            throw new NullPointerException("dto is null");
        }
        
        Optional<TId> dtoId = dto.getId();
        
        if (dtoId.isEmpty() && target != null) {
            throw new IllegalArgumentException(
                    "dto does not provide an identifier but target is not "
                            + "null!");
        }
        
        if (dtoId.isPresent() && target == null) {
            throw new IllegalArgumentException(
                    "dto provides an identifier but target is null!");
        }
        
        if (dtoId.isPresent() &&
                target != null &&
                Objects.equals(dtoId.get(), this.getId(target))) {
            throw new IllegalArgumentException(
                    "dto provides another id than target!");
        }
        
        TEntity result = this.createOrUpdateEntity(dto, target);
        
        if (result == null) {
            throw new IllegalStateException(String.format(
                    "The createOrUpdateEntity(TDto, TEntity) implementation of "
                            + "%s returned a null pointer which is not "
                            + "allowed!",
                    this.getClass().getName()));
        }
        
        return result;
    }
}
