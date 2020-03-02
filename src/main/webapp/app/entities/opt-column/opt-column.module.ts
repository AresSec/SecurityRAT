import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { OptColumnComponent } from './opt-column.component';
import { OptColumnDetailComponent } from './opt-column-detail.component';
import { OptColumnUpdateComponent } from './opt-column-update.component';
import { OptColumnDeleteDialogComponent } from './opt-column-delete-dialog.component';
import { optColumnRoute } from './opt-column.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(optColumnRoute)],
  declarations: [OptColumnComponent, OptColumnDetailComponent, OptColumnUpdateComponent, OptColumnDeleteDialogComponent],
  entryComponents: [OptColumnDeleteDialogComponent]
})
export class SecurityRatOptColumnModule {}
