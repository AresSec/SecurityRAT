import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { OptColumnContentComponent } from './opt-column-content.component';
import { OptColumnContentDetailComponent } from './opt-column-content-detail.component';
import { OptColumnContentUpdateComponent } from './opt-column-content-update.component';
import { OptColumnContentDeleteDialogComponent } from './opt-column-content-delete-dialog.component';
import { optColumnContentRoute } from './opt-column-content.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(optColumnContentRoute)],
  declarations: [
    OptColumnContentComponent,
    OptColumnContentDetailComponent,
    OptColumnContentUpdateComponent,
    OptColumnContentDeleteDialogComponent
  ],
  entryComponents: [OptColumnContentDeleteDialogComponent]
})
export class SecurityRatOptColumnContentModule {}
