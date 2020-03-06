import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { TrainingGeneratedSlideNodeComponent } from './training-generated-slide-node.component';
import { TrainingGeneratedSlideNodeDetailComponent } from './training-generated-slide-node-detail.component';
import { TrainingGeneratedSlideNodeUpdateComponent } from './training-generated-slide-node-update.component';
import { TrainingGeneratedSlideNodeDeleteDialogComponent } from './training-generated-slide-node-delete-dialog.component';
import { trainingGeneratedSlideNodeRoute } from './training-generated-slide-node.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(trainingGeneratedSlideNodeRoute)],
  declarations: [
    TrainingGeneratedSlideNodeComponent,
    TrainingGeneratedSlideNodeDetailComponent,
    TrainingGeneratedSlideNodeUpdateComponent,
    TrainingGeneratedSlideNodeDeleteDialogComponent
  ],
  entryComponents: [TrainingGeneratedSlideNodeDeleteDialogComponent]
})
export class SecurityRatTrainingGeneratedSlideNodeModule {}
