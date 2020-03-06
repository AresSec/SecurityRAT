import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ISlideTemplate } from 'app/shared/model/slide-template.model';

type EntityResponseType = HttpResponse<ISlideTemplate>;
type EntityArrayResponseType = HttpResponse<ISlideTemplate[]>;

@Injectable({ providedIn: 'root' })
export class SlideTemplateService {
  public resourceUrl = SERVER_API_URL + 'api/slide-templates';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/slide-templates';

  constructor(protected http: HttpClient) {}

  create(slideTemplate: ISlideTemplate): Observable<EntityResponseType> {
    return this.http.post<ISlideTemplate>(this.resourceUrl, slideTemplate, { observe: 'response' });
  }

  update(slideTemplate: ISlideTemplate): Observable<EntityResponseType> {
    return this.http.put<ISlideTemplate>(this.resourceUrl, slideTemplate, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISlideTemplate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISlideTemplate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISlideTemplate[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
