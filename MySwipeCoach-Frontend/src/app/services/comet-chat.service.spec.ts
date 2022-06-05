import { TestBed } from '@angular/core/testing';

import { CometChatService } from './comet-chat.service';

describe('CometChatService', () => {
  let service: CometChatService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CometChatService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
