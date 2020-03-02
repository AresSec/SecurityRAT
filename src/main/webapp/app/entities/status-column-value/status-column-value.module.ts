import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { StatusColumnValueComponent } from './status-column-value.component';
import { StatusColumnValueDetailComponent } from './status-column-value-detail.component';
import { StatusColumnValueUpdateComponent } from './status-column-value-update.component';
import { StatusColumnValueDeleteDialogComponent } from './status-column-value-delete-dialog.component';
import { statusColumnValueRoute } from './status-column-value.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(statusColumnValueRoute)],
  declarations: [
    StatusColumnValueComponent,
    StatusColumnValueDetailComponent,
    StatusColumnValueUpdateComponent,
    StatusColumnValueDeleteDialogComponent
  ],
  entryComponents: [StatusColumnValueDeleteDialogComponent]
})
export class SecurityRatStatusColumnValueModule {}
