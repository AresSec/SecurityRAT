import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ITagInstance } from 'app/shared/model/tag-instance.model';

type EntityResponseType = HttpResponse<ITagInstance>;
type EntityArrayResponseType = HttpResponse<ITagInstance[]>;

@Injectable({ providedIn: 'root' })
export class TagInstanceService {
  public resourceUrl = SERVER_API_URL + 'api/tag-instances';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/tag-instances';

  constructor(protected http: HttpClient) {}

  create(tagInstance: ITagInstance): Observable<EntityResponseType> {
    return this.http.post<ITagInstance>(this.resourceUrl, tagInstance, { observe: 'response' });
  }

  update(tagInstance: ITagInstance): Observable<EntityResponseType> {
    return this.http.put<ITagInstance>(this.resourceUrl, tagInstance, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITagInstance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITagInstance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITagInstance[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
