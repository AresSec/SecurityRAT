package org.appsec.securityrat.provider;

public abstract class AbstractDtoMapper<TDto, TEntity> {
    /**
     * Creates a data transfer object from the passed <code>entity</code>.
     * 
     * <p>
     * <b>Note:</b>
     * Please do not call this method directly as implementations are not
     * required to expect that the passed <code>entity</code> is
     * <code>null</code>. Please use {@link #createDtoChecked(java.lang.Object)}
     * instead.
     * 
     * @param entity The entity that will be used for creating the resulting
     *               data transfer object.
     * 
     * @return The data transfer object that was derived from the passed
     *         <code>entity</code>.
     * 
     * @see #createDtoChecked(java.lang.Object)
     */
    protected abstract TDto createDto(TEntity entity);
    
    /**
     * Creates a data transfer object form the passed <code>entity</code>.
     * 
     * @param entity The entity that will be sued for creating the resulting
     *               data transfer object.
     * 
     * @return Either the data transfer object that was derived from the passed
     *         <code>entity</code>, or <code>null</code>, if the passed
     *         <code>entity</code> is <code>null</code>.
     */
    public TDto createDtoChecked(TEntity entity) {
        if (entity == null) {
            return null;
        }
        
        TDto dto = this.createDto(entity);
        
        if (dto == null) {
            throw new IllegalStateException(String.format(
                    "The createDto(Object) implementation of %s returned a "
                            + "null pointer which is not allowed!",
                    this.getClass().getName()));
        }
        
        return dto;
    }
}
