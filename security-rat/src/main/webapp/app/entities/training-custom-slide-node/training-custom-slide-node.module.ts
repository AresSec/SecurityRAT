import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { TrainingCustomSlideNodeComponent } from './training-custom-slide-node.component';
import { TrainingCustomSlideNodeDetailComponent } from './training-custom-slide-node-detail.component';
import { TrainingCustomSlideNodeUpdateComponent } from './training-custom-slide-node-update.component';
import { TrainingCustomSlideNodeDeleteDialogComponent } from './training-custom-slide-node-delete-dialog.component';
import { trainingCustomSlideNodeRoute } from './training-custom-slide-node.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(trainingCustomSlideNodeRoute)],
  declarations: [
    TrainingCustomSlideNodeComponent,
    TrainingCustomSlideNodeDetailComponent,
    TrainingCustomSlideNodeUpdateComponent,
    TrainingCustomSlideNodeDeleteDialogComponent
  ],
  entryComponents: [TrainingCustomSlideNodeDeleteDialogComponent]
})
export class SecurityRatTrainingCustomSlideNodeModule {}
