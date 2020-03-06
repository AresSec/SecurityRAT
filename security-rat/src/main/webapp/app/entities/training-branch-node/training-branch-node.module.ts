import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { TrainingBranchNodeComponent } from './training-branch-node.component';
import { TrainingBranchNodeDetailComponent } from './training-branch-node-detail.component';
import { TrainingBranchNodeUpdateComponent } from './training-branch-node-update.component';
import { TrainingBranchNodeDeleteDialogComponent } from './training-branch-node-delete-dialog.component';
import { trainingBranchNodeRoute } from './training-branch-node.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(trainingBranchNodeRoute)],
  declarations: [
    TrainingBranchNodeComponent,
    TrainingBranchNodeDetailComponent,
    TrainingBranchNodeUpdateComponent,
    TrainingBranchNodeDeleteDialogComponent
  ],
  entryComponents: [TrainingBranchNodeDeleteDialogComponent]
})
export class SecurityRatTrainingBranchNodeModule {}
