import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { ProjectTypeComponent } from './project-type.component';
import { ProjectTypeDetailComponent } from './project-type-detail.component';
import { ProjectTypeUpdateComponent } from './project-type-update.component';
import { ProjectTypeDeleteDialogComponent } from './project-type-delete-dialog.component';
import { projectTypeRoute } from './project-type.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(projectTypeRoute)],
  declarations: [ProjectTypeComponent, ProjectTypeDetailComponent, ProjectTypeUpdateComponent, ProjectTypeDeleteDialogComponent],
  entryComponents: [ProjectTypeDeleteDialogComponent]
})
export class SecurityRatProjectTypeModule {}
