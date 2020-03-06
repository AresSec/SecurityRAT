import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { AlternativeInstanceComponent } from './alternative-instance.component';
import { AlternativeInstanceDetailComponent } from './alternative-instance-detail.component';
import { AlternativeInstanceUpdateComponent } from './alternative-instance-update.component';
import { AlternativeInstanceDeleteDialogComponent } from './alternative-instance-delete-dialog.component';
import { alternativeInstanceRoute } from './alternative-instance.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(alternativeInstanceRoute)],
  declarations: [
    AlternativeInstanceComponent,
    AlternativeInstanceDetailComponent,
    AlternativeInstanceUpdateComponent,
    AlternativeInstanceDeleteDialogComponent
  ],
  entryComponents: [AlternativeInstanceDeleteDialogComponent]
})
export class SecurityRatAlternativeInstanceModule {}
