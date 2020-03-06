import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { TrainingCategoryNodeComponent } from './training-category-node.component';
import { TrainingCategoryNodeDetailComponent } from './training-category-node-detail.component';
import { TrainingCategoryNodeUpdateComponent } from './training-category-node-update.component';
import { TrainingCategoryNodeDeleteDialogComponent } from './training-category-node-delete-dialog.component';
import { trainingCategoryNodeRoute } from './training-category-node.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(trainingCategoryNodeRoute)],
  declarations: [
    TrainingCategoryNodeComponent,
    TrainingCategoryNodeDetailComponent,
    TrainingCategoryNodeUpdateComponent,
    TrainingCategoryNodeDeleteDialogComponent
  ],
  entryComponents: [TrainingCategoryNodeDeleteDialogComponent]
})
export class SecurityRatTrainingCategoryNodeModule {}
