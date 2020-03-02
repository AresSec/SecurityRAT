import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'alternative-instance',
        loadChildren: () => import('./alternative-instance/alternative-instance.module').then(m => m.SecurityRatAlternativeInstanceModule)
      },
      {
        path: 'alternative-set',
        loadChildren: () => import('./alternative-set/alternative-set.module').then(m => m.SecurityRatAlternativeSetModule)
      },
      {
        path: 'collection-category',
        loadChildren: () => import('./collection-category/collection-category.module').then(m => m.SecurityRatCollectionCategoryModule)
      },
      {
        path: 'collection-instance',
        loadChildren: () => import('./collection-instance/collection-instance.module').then(m => m.SecurityRatCollectionInstanceModule)
      },
      {
        path: 'opt-column',
        loadChildren: () => import('./opt-column/opt-column.module').then(m => m.SecurityRatOptColumnModule)
      },
      {
        path: 'opt-column-content',
        loadChildren: () => import('./opt-column-content/opt-column-content.module').then(m => m.SecurityRatOptColumnContentModule)
      },
      {
        path: 'opt-column-type',
        loadChildren: () => import('./opt-column-type/opt-column-type.module').then(m => m.SecurityRatOptColumnTypeModule)
      },
      {
        path: 'project-type',
        loadChildren: () => import('./project-type/project-type.module').then(m => m.SecurityRatProjectTypeModule)
      },
      {
        path: 'req-category',
        loadChildren: () => import('./req-category/req-category.module').then(m => m.SecurityRatReqCategoryModule)
      },
      {
        path: 'requirement-skeleton',
        loadChildren: () => import('./requirement-skeleton/requirement-skeleton.module').then(m => m.SecurityRatRequirementSkeletonModule)
      },
      {
        path: 'slide-template',
        loadChildren: () => import('./slide-template/slide-template.module').then(m => m.SecurityRatSlideTemplateModule)
      },
      {
        path: 'status-column',
        loadChildren: () => import('./status-column/status-column.module').then(m => m.SecurityRatStatusColumnModule)
      },
      {
        path: 'status-column-value',
        loadChildren: () => import('./status-column-value/status-column-value.module').then(m => m.SecurityRatStatusColumnValueModule)
      },
      {
        path: 'tag-category',
        loadChildren: () => import('./tag-category/tag-category.module').then(m => m.SecurityRatTagCategoryModule)
      },
      {
        path: 'tag-instance',
        loadChildren: () => import('./tag-instance/tag-instance.module').then(m => m.SecurityRatTagInstanceModule)
      },
      {
        path: 'training',
        loadChildren: () => import('./training/training.module').then(m => m.SecurityRatTrainingModule)
      },
      {
        path: 'training-branch-node',
        loadChildren: () => import('./training-branch-node/training-branch-node.module').then(m => m.SecurityRatTrainingBranchNodeModule)
      },
      {
        path: 'training-category-node',
        loadChildren: () =>
          import('./training-category-node/training-category-node.module').then(m => m.SecurityRatTrainingCategoryNodeModule)
      },
      {
        path: 'training-custom-slide-node',
        loadChildren: () =>
          import('./training-custom-slide-node/training-custom-slide-node.module').then(m => m.SecurityRatTrainingCustomSlideNodeModule)
      },
      {
        path: 'training-generated-slide-node',
        loadChildren: () =>
          import('./training-generated-slide-node/training-generated-slide-node.module').then(
            m => m.SecurityRatTrainingGeneratedSlideNodeModule
          )
      },
      {
        path: 'training-requirement-node',
        loadChildren: () =>
          import('./training-requirement-node/training-requirement-node.module').then(m => m.SecurityRatTrainingRequirementNodeModule)
      },
      {
        path: 'training-tree-node',
        loadChildren: () => import('./training-tree-node/training-tree-node.module').then(m => m.SecurityRatTrainingTreeNodeModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class SecurityRatEntityModule {}
