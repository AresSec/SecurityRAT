import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { TagInstanceComponent } from './tag-instance.component';
import { TagInstanceDetailComponent } from './tag-instance-detail.component';
import { TagInstanceUpdateComponent } from './tag-instance-update.component';
import { TagInstanceDeleteDialogComponent } from './tag-instance-delete-dialog.component';
import { tagInstanceRoute } from './tag-instance.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(tagInstanceRoute)],
  declarations: [TagInstanceComponent, TagInstanceDetailComponent, TagInstanceUpdateComponent, TagInstanceDeleteDialogComponent],
  entryComponents: [TagInstanceDeleteDialogComponent]
})
export class SecurityRatTagInstanceModule {}
