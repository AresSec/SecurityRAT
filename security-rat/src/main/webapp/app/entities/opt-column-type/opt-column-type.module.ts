import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { OptColumnTypeComponent } from './opt-column-type.component';
import { OptColumnTypeDetailComponent } from './opt-column-type-detail.component';
import { OptColumnTypeUpdateComponent } from './opt-column-type-update.component';
import { OptColumnTypeDeleteDialogComponent } from './opt-column-type-delete-dialog.component';
import { optColumnTypeRoute } from './opt-column-type.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(optColumnTypeRoute)],
  declarations: [OptColumnTypeComponent, OptColumnTypeDetailComponent, OptColumnTypeUpdateComponent, OptColumnTypeDeleteDialogComponent],
  entryComponents: [OptColumnTypeDeleteDialogComponent]
})
export class SecurityRatOptColumnTypeModule {}
