import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { TrainingTreeNodeComponent } from './training-tree-node.component';
import { TrainingTreeNodeDetailComponent } from './training-tree-node-detail.component';
import { TrainingTreeNodeUpdateComponent } from './training-tree-node-update.component';
import { TrainingTreeNodeDeleteDialogComponent } from './training-tree-node-delete-dialog.component';
import { trainingTreeNodeRoute } from './training-tree-node.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(trainingTreeNodeRoute)],
  declarations: [
    TrainingTreeNodeComponent,
    TrainingTreeNodeDetailComponent,
    TrainingTreeNodeUpdateComponent,
    TrainingTreeNodeDeleteDialogComponent
  ],
  entryComponents: [TrainingTreeNodeDeleteDialogComponent]
})
export class SecurityRatTrainingTreeNodeModule {}
