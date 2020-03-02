import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { AlternativeSetComponent } from './alternative-set.component';
import { AlternativeSetDetailComponent } from './alternative-set-detail.component';
import { AlternativeSetUpdateComponent } from './alternative-set-update.component';
import { AlternativeSetDeleteDialogComponent } from './alternative-set-delete-dialog.component';
import { alternativeSetRoute } from './alternative-set.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(alternativeSetRoute)],
  declarations: [
    AlternativeSetComponent,
    AlternativeSetDetailComponent,
    AlternativeSetUpdateComponent,
    AlternativeSetDeleteDialogComponent
  ],
  entryComponents: [AlternativeSetDeleteDialogComponent]
})
export class SecurityRatAlternativeSetModule {}
