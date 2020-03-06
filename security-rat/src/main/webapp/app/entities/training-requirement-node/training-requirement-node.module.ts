import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { TrainingRequirementNodeComponent } from './training-requirement-node.component';
import { TrainingRequirementNodeDetailComponent } from './training-requirement-node-detail.component';
import { TrainingRequirementNodeUpdateComponent } from './training-requirement-node-update.component';
import { TrainingRequirementNodeDeleteDialogComponent } from './training-requirement-node-delete-dialog.component';
import { trainingRequirementNodeRoute } from './training-requirement-node.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(trainingRequirementNodeRoute)],
  declarations: [
    TrainingRequirementNodeComponent,
    TrainingRequirementNodeDetailComponent,
    TrainingRequirementNodeUpdateComponent,
    TrainingRequirementNodeDeleteDialogComponent
  ],
  entryComponents: [TrainingRequirementNodeDeleteDialogComponent]
})
export class SecurityRatTrainingRequirementNodeModule {}
