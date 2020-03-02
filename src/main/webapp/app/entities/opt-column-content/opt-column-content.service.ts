import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IOptColumnContent } from 'app/shared/model/opt-column-content.model';

type EntityResponseType = HttpResponse<IOptColumnContent>;
type EntityArrayResponseType = HttpResponse<IOptColumnContent[]>;

@Injectable({ providedIn: 'root' })
export class OptColumnContentService {
  public resourceUrl = SERVER_API_URL + 'api/opt-column-contents';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/opt-column-contents';

  constructor(protected http: HttpClient) {}

  create(optColumnContent: IOptColumnContent): Observable<EntityResponseType> {
    return this.http.post<IOptColumnContent>(this.resourceUrl, optColumnContent, { observe: 'response' });
  }

  update(optColumnContent: IOptColumnContent): Observable<EntityResponseType> {
    return this.http.put<IOptColumnContent>(this.resourceUrl, optColumnContent, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOptColumnContent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOptColumnContent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOptColumnContent[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
