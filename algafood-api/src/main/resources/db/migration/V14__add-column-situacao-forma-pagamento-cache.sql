alter table forma_pagamento add situacao varchar(12) null;
update forma_pagamento set situacao = 'ATIVO';
alter table forma_pagamento modify situacao varchar(12) not null;