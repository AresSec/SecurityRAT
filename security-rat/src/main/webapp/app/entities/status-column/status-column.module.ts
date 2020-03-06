import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { StatusColumnComponent } from './status-column.component';
import { StatusColumnDetailComponent } from './status-column-detail.component';
import { StatusColumnUpdateComponent } from './status-column-update.component';
import { StatusColumnDeleteDialogComponent } from './status-column-delete-dialog.component';
import { statusColumnRoute } from './status-column.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(statusColumnRoute)],
  declarations: [StatusColumnComponent, StatusColumnDetailComponent, StatusColumnUpdateComponent, StatusColumnDeleteDialogComponent],
  entryComponents: [StatusColumnDeleteDialogComponent]
})
export class SecurityRatStatusColumnModule {}
