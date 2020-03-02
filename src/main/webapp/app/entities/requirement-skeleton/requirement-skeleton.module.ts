import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { RequirementSkeletonComponent } from './requirement-skeleton.component';
import { RequirementSkeletonDetailComponent } from './requirement-skeleton-detail.component';
import { RequirementSkeletonUpdateComponent } from './requirement-skeleton-update.component';
import { RequirementSkeletonDeleteDialogComponent } from './requirement-skeleton-delete-dialog.component';
import { requirementSkeletonRoute } from './requirement-skeleton.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(requirementSkeletonRoute)],
  declarations: [
    RequirementSkeletonComponent,
    RequirementSkeletonDetailComponent,
    RequirementSkeletonUpdateComponent,
    RequirementSkeletonDeleteDialogComponent
  ],
  entryComponents: [RequirementSkeletonDeleteDialogComponent]
})
export class SecurityRatRequirementSkeletonModule {}
